package com.aleksx.teleshorter.shorter;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("teleshorter")
@Data
public class TeleshorterProps {

    private String urlDomain;
}
