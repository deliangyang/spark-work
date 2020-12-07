package com.party.log.stat

import java.beans.BeanProperty

import com.fasterxml.jackson.annotation.{JsonIgnoreProperties, JsonProperty}

@JsonIgnoreProperties(ignoreUnknown = true)
case class
Log(
     @JsonProperty("user") @BeanProperty user: String,
     @JsonProperty("time") @BeanProperty time: String
   )
