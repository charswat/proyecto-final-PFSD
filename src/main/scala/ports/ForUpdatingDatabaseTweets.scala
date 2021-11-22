package eci.edu.co
package ports

import entities.sparks.Procesamiento.{UserRetweetsFollowers, UserTweetsLanguage}
trait ForUpdatingDatabaseTweets {

  def userRetweetsFollowers(userRetweetsFollowers: List[UserRetweetsFollowers])

  def usertweetsLanguage(userTweetsLanguage: List[UserTweetsLanguage])

}
