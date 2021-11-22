package eci.edu.co
package kafka.producers

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import twitter4j._
import twitter4j.conf.ConfigurationBuilder

import java.io.FileInputStream
import java.util.Properties
object TweetProducer extends App {

  val BROKER_LIST = "localhost:9093" //change it to localhost:9092 if not connecting through docker
  val TOPIC = "tweets"

  val props = new Properties()
  props.put("bootstrap.servers", BROKER_LIST)
  props.put("client.id", "KafkaTweetProducer")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")


  //get properties
  val (consumerKey, consumerSecret, accessToken, accessTokenSecret) =
    try {
      val prop = new Properties()
      prop.load(new FileInputStream("C:/Users/chars/Desktop/Nueva carpeta/Twitter-Kafka-Docker-master/TwitterHtagKafkaProducer/src/main/Resources/twitter.properties"))
      (
        prop.getProperty("consumerKey"),
        prop.getProperty("consumerSecret"),
        prop.getProperty("accessToken"),
        prop.getProperty("accessTokenSecret")
      )
    } catch { case e: Exception =>
      e.printStackTrace()
      sys.exit(1)
    }

  val producer = new KafkaProducer[String, String](props)

  def startTweetStream() = {
    val config = new ConfigurationBuilder()
    config.setDebugEnabled(true)
    config.setOAuthConsumerKey(consumerKey)  //replace this with your own keys
    config.setOAuthConsumerSecret(consumerSecret)  //replace this with your own keys
    config.setOAuthAccessToken(accessToken)  //replace this with your own keys
    config.setOAuthAccessTokenSecret(accessTokenSecret)  //replace this with your own keys
    config.setJSONStoreEnabled(true)
    val stream = new TwitterStreamFactory(config.build()).getInstance()
    val listener = new StatusListener {

      override def onTrackLimitationNotice(i: Int) = println(s"Track limited $i tweets")
      override def onStallWarning(stallWarning: StallWarning) = println("Stream stalled")
      override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) = println("Status ${statusDeletionNotice.getStatusId} deleted")
      override def onScrubGeo(l: Long, l1: Long) = println(s"Geo info scrubbed. userId:$l, upToStatusId:$l1")
      override def onException(e: Exception) = println("Exception occurred. " + e.getMessage)
      override def onStatus(status: Status): Unit = {
        val tweet = status
        import twitter4j.json.DataObjectFactory
        val json = DataObjectFactory.getRawJSON(tweet)
        println("[Producer] " + json)
        val data = new ProducerRecord[String,String](TOPIC, json)
        producer.send(data)

      }

    }
    stream.addListener(listener)
    val fq = new FilterQuery()
    fq.track("CopaAmericaPFSD")
    stream.filter(fq)
    stream.sample()

  }

  startTweetStream()
}