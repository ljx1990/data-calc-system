package com.hakim.clac.billing

import akka.actor.{Actor, ActorLogging}

/**
  * Created by Administrator on 2017/2/25 0025.
  */

case class PreCalcBill()
case class AfterCalcBill()
case class CompeleteCalcBill()

class BillingActor extends Actor with ActorLogging  {


  def receive = {
    case msg: PreCalcBill =>
      log.info("记录日志")
    case msg: AfterCalcBill =>
      log.info("记录日志")
    case msg: CompeleteCalcBill =>
      log.info("记录日志")

  }
}
