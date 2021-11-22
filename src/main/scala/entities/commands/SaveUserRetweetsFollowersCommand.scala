package eci.edu.co
package entities.commands

import entities.sparks.Procesamiento.UserRetweetsFollowers
import ports.ForUpdatingDatabaseTweets

case class SaveUserRetweetsFollowersCommand(forUpdatingDatabaseTweets: ForUpdatingDatabaseTweets, userRetweetsFollowers:List[UserRetweetsFollowers]) extends Command with Serializable {

  override def execute(): Unit = {
    forUpdatingDatabaseTweets.userRetweetsFollowers(userRetweetsFollowers)
  }

}