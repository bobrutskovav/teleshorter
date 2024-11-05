package com.aleksx.teleshorter.shorter;

import com.aleksx.teleshorter.shorter.dto.ShortUrlDto;
import com.aleksx.teleshorter.shorter.model.ShortUrlEntity;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
class DefaultShorterLink implements ShorterLink {


    private ShortLinkRepository shortLinkRepository;
    private ReactiveMongoTemplate mongoTemplate;
    private TeleshorterProps teleshorterProps;


    @Override
    public Mono<ShortUrlDto> shortUri(URI uri) {

        //find url in cache service
        return shortLinkRepository.findByrUrl(uri.toString())
                .map(this::mapToDto)
                .switchIfEmpty(Mono.fromSupplier(() -> {
                    var newId = RandomStringUtils.randomAlphanumeric(4, 8);
                    var sUrl = URI.create(teleshorterProps.getUrlDomain()).resolve(newId);
                    return new ShortUrlEntity(newId,
                            uri.toString(),
                            sUrl.toString(),
                            true,
                            0L,
                            Instant.now().plus(183, ChronoUnit.DAYS));

                }).flatMap(shortUrlEntity ->
                        shortLinkRepository.save(shortUrlEntity)
                                .map(this::mapToDto)));


    }

    @Override
    public Mono<URI> getFullUrlById(String id) {

        Query findQuery = new Query().addCriteria(Criteria.where("_id").is(id));
        Update update = new Update().inc("clickCount", 1);
        return mongoTemplate.findAndModify(findQuery, update, ShortUrlEntity.class)
                .handle((shortUrlEntity, sink) -> {
                    try {
                        sink.next(new URI(shortUrlEntity.rUrl()));
                    } catch (URISyntaxException e) {
                        sink.error(new RuntimeException(e));
                    }
                });
    }


    private ShortUrlDto mapToDto(ShortUrlEntity it) {
        return new ShortUrlDto(it.id(), URI.create(it.rUrl()), URI.create(it.sUrl()),
                it.isActive(), OffsetDateTime.ofInstant(it.activeDate(), ZoneId.systemDefault()));
    }


}
