package eci.edu.co
package adapters

import ports.ForTakingBackupTweets

import com.typesafe.scalalogging.Logger

import java.io.{BufferedWriter, FileWriter}

object PlainTextFileTweets extends ForTakingBackupTweets {
  val LOGGER = Logger("PlainTextFileTweets")

  override def backupTweets(tweets: String): Unit = {
    try {
      val bw = new BufferedWriter(new FileWriter("TweetsBackup.json", true))
      bw.write(tweets)
      bw.newLine()
      bw.close()
    } catch {
      case e: Exception => LOGGER.error(s"There was an error saving the backup: ${e.getMessage}")
    }
  }
}
