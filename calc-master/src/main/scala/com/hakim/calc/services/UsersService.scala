package com.hakim.calc.services


import scala.concurrent.{ExecutionContext, Future}

class UsersService(val databaseService: DatabaseService)(implicit executionContext: ExecutionContext) extends UserEntityTable {

  def getUsers(): Future[Seq[UserEntity]] = db.run(users.result)

  def getUserById(id: Long): Future[Option[UserEntity]] = db.run(users.filter(_.id === id).result.headOption)

  def getUserByLogin(login: String): Future[Option[UserEntity]] = db.run(users.filter(_.username === login).result.headOption)

  def createUser(user: UserEntity): Future[UserEntity] = db.run(users returning users += user)

  def updateUser(id: Long, userUpdate: UserEntityUpdate): Future[Option[UserEntity]] = ???
  def deleteUser(id: Long): Future[Int] = ???

}