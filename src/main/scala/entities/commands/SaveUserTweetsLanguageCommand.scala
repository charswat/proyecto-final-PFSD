package eci.edu.co
package entities.commands

import entities.sparks.Procesamiento.UserTweetsLanguage
import ports.ForUpdatingDatabaseTweets

case class SaveUserTweetsLanguageCommand(forUpdatingDatabase: ForUpdatingDatabaseTweets,userTweetsLanguage: List[UserTweetsLanguage]) extends Command {

  override def execute(): Unit = {
    forUpdatingDatabase.usertweetsLanguage(userTweetsLanguage)
  }

}