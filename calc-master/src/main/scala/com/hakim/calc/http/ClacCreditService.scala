package com.hakim.calc.http


import akka.http.scaladsl.server.Directives._
import com.hakim.calc.http.routes.{AuthServiceRoute, UsersServiceRoute}
import com.hakim.calc.services.{AuthService, UsersService}
import com.hakim.calc.utils.CorsSupport

import scala.concurrent.ExecutionContext

class ClacCreditService(usersService: UsersService,
                  authService: AuthService
                 )(implicit executionContext: ExecutionContext) extends CorsSupport {

  val usersRouter = new UsersServiceRoute(authService, usersService)
  val authRouter = new AuthServiceRoute(authService)

  val routes =
    pathPrefix("v1") {
      corsHandler {
        usersRouter.route ~
          authRouter.route
      }
    }

}