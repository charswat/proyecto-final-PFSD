package eci.edu.co
package adapters


import ports.ForUpdatingDatabaseTweets
import postgresql.TweetsDAO
import entities.sparks.Procesamiento.UserRetweetsFollowers
import entities.sparks.Procesamiento.UserTweetsLanguage
import com.typesafe.scalalogging.Logger

import java.sql.{Connection, DriverManager}

object PostgresRepositoryTweets extends ForUpdatingDatabaseTweets {

  val LOGGER = Logger("PostgresRepository")
  val transactionDAO = getTransactionDAO

  private def getTransactionDAO = {
    val url = "jdbc:postgresql://localhost:5438/postgres"
    val user = "postgres"
    val password = "postgres"
    val connection: Connection = DriverManager.getConnection(url, user, password)
    TweetsDAO(connection)
  }

  def saveUserRetweetsFollowers(userRetweetsFollowers: List[UserRetweetsFollowers]) = {
    transactionDAO.saveuserRetweetsFollowers(userRetweetsFollowers) match {
      case Left(e) => {
        LOGGER.error(s"There was an error saving el tweets: ${e.getMessage}")
        throw e
      }
      case Right(_) => LOGGER.info("Record inserted")
    }
  }

  def saveuserTweetsLanguage(userTweetsLanguage: List[UserTweetsLanguage]) = {
    transactionDAO.saveUserTweetsLanguage(userTweetsLanguage) match {
      case Left(e) => {
        LOGGER.error(s"There was an error saving el tweets: ${e.getMessage}")
        throw e
      }
      case Right(_) => LOGGER.info("Record inserted")
    }
  }

  override def userRetweetsFollowers(userRetweetsFollowers: List[UserRetweetsFollowers])=saveUserRetweetsFollowers(userRetweetsFollowers)

  override def usertweetsLanguage(userTweetsLanguage: List[UserTweetsLanguage]) = saveuserTweetsLanguage(userTweetsLanguage)
}
