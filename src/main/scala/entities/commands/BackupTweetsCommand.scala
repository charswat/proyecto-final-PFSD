package eci.edu.co
package entities.commands

import ports.ForTakingBackupTweets

case class BackupTweetsCommand(forTakingBackupTweets: ForTakingBackupTweets, tweet: String) extends Command {

  override def execute(): Unit =forTakingBackupTweets.backupTweets(tweet)

}
