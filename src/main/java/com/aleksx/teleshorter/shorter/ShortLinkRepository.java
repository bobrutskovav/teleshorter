package com.aleksx.teleshorter.shorter;

import com.aleksx.teleshorter.shorter.model.ShortUrlEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;


public interface ShortLinkRepository extends ReactiveMongoRepository<ShortUrlEntity, String> {


    Mono<ShortUrlEntity> findByrUrl(String rUrl);

}
