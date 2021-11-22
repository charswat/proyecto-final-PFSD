package eci.edu.co
package kafka.consumers
import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.serialization.Serdes._

import java.util.Properties
object TweetConsumer extends App {

  val LOGGER = Logger("Consumer")
  LOGGER.info("Starting Consumer")

  val kafkaStreamProps: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9093")
    props.put("application.id", "consumer-stream-sample")
    //    props.put("application.id", "consumer-stream-sample-two")
    //    props.put("auto.offset.reset", "latest")
    props
  }

  val streams = new KafkaStreams(streamTopology, kafkaStreamProps)
  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
  streams.start()

  def streamTopology = {
    val streamsBuilder = new StreamsBuilder()
    streamsBuilder
      .stream[String, String]("tweets")
      .foreach((key, value) =>
        LOGGER.info(s"Read message with key: $key and value $value")
      )
    streamsBuilder.build()
  }

}
