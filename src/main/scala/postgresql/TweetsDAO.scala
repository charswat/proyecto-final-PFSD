package eci.edu.co
package postgresql
import entities.sparks.Procesamiento.UserRetweetsFollowers
import entities.sparks.Procesamiento.UserTweetsLanguage

import java.sql.Connection

case class TweetsDAO(connection: Connection) {
  val sql = "INSERT INTO UserRetweetsFollowers (Usuarios,Seguidores,Num_retweets) VALUES(?, ?, ?)"
  val sql2 = "INSERT INTO UserTweetsLanguage (Usuarios,idioma,cantidad) VALUES(?, ?, ?)"

  def saveuserRetweetsFollowers(userRetweetsFollowers: List[UserRetweetsFollowers]): Either[Throwable, Int] = {
    try {
      val preparedStatement = connection.prepareStatement(sql)
      preparedStatement.setString(1, userRetweetsFollowers.foreach(u=> u.Usuarios).toString)
      preparedStatement.setString(2, userRetweetsFollowers.foreach(u => u.Seguidores).toString)
      preparedStatement.setString(3, userRetweetsFollowers.foreach(u => u.Num_retweets).toString)
      Right(preparedStatement.executeUpdate())
    } catch {
      case e: Exception => Left(e)
    }
  }
  def saveUserTweetsLanguage(userTweetsLanguage: List[UserTweetsLanguage]): Either[Throwable, Int] = {
    try {
      val preparedStatement = connection.prepareStatement(sql2)
      preparedStatement.setString(1, userTweetsLanguage.foreach(l=> l.Usuarios).toString)
      preparedStatement.setString(2,  userTweetsLanguage.foreach(l=> l.idioma).toString)
      preparedStatement.setString(3,  userTweetsLanguage.foreach(l=> l.cantidad).toString)
      Right(preparedStatement.executeUpdate())
    } catch {
      case e: Exception => Left(e)
    }
  }
}
