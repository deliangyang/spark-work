package com.party.log.stat

import java.beans.BeanProperty

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
case class
Log(
     @JsonProperty("user") @BeanProperty user: String,
     @JsonProperty("time") @BeanProperty time: String,
     @JsonProperty("path") @BeanProperty path: String,
     @JsonProperty("method") @BeanProperty method: String
   )
