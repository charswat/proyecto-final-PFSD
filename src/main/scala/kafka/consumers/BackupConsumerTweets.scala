package eci.edu.co
package kafka.consumers
import adapters.PlainTextFileTweets
import entities.commands.BackupTweetsCommand

import com.typesafe.scalalogging.Logger
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.StreamsBuilder
import org.apache.kafka.streams.scala.serialization.Serdes._

import java.util.Properties

object BackupConsumerTweets extends App {
  val LOGGER = Logger("backup-consumer-tweets")
  LOGGER.info("Starting backup-consumer-tweets")

  val kafkaStreamProps: Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9093")
    props.put("application.id", "backup-consumer-tweets")
    props
  }

  val streams = new KafkaStreams(streamTopology, kafkaStreamProps)
  Runtime.getRuntime.addShutdownHook(new Thread(() => streams.close()))
  streams.start()


  def streamTopology = {
    val streamsBuilder = new StreamsBuilder()
    streamsBuilder
      .stream[String, String]("tweets")
      .foreach(
        (key, value) => {
          LOGGER.info(s"Read message with key: $key and value $value")
         // val deserializedTransaction = Transaction.fromTransactionMessage(TransactionMessage.parseFrom(value))
          val deserializedTransaction = value
         // val deserializedTransaction = TweetProducer.
          LOGGER.info(s"Deserialized message: $deserializedTransaction")
          BackupTweetsCommand(PlainTextFileTweets, deserializedTransaction).execute()
        }
      )
    streamsBuilder.build()
  }
}
