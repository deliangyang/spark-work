package com.error.log

import java.beans.BeanProperty

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
case class
StandardLog(
             @JsonProperty("t") @BeanProperty t: String,
             @JsonProperty("l") @BeanProperty l: String,
             @JsonProperty("s") @BeanProperty s: String,
             @JsonProperty("c") @BeanProperty c: String,
             @JsonProperty("e") @BeanProperty e: String,
             @JsonProperty("m") @BeanProperty m: String
           )
