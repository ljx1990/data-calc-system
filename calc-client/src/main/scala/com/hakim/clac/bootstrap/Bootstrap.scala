package com.hakim.clac.bootstrap

import akka.actor._
import com.hakim.clac.compute.{CalcAccessJobActor, CalcCreditJobActor}

object HelloRemote extends App  {
  val system = ActorSystem("clac-client")
  val remoteActor = system.actorOf(Props[WorkerDispatcherActor], name = "WorkerDispatcherActor")

  remoteActor ! "The RemoteActor is alive1"
  remoteActor ! "The RemoteActor is alive2"
  remoteActor ! "The RemoteActor is alive3"
  remoteActor ! "The RemoteActor is alive4"
  remoteActor ! "The RemoteActor is alive5"
  remoteActor ! "The RemoteActor is alive6"

}

trait CalcModelBase{
  def transactionId:String
  def calcItemIds:List[String]
  def dataItemIds:List[String]
}



/** 信用分计算 */
case class CreditCalc(transactionId:String,calcItemIds:List[String],dataItemIds:List[String]) extends CalcModelBase
/** 准入评分计算 */
case class AccessCalc(transactionId:String,calcItemIds:List[String],dataItemIds:List[String]) extends CalcModelBase

/**
  * 任务接收总线
  */
class WorkerDispatcherActor extends Actor with ActorLogging  {



  def receive = {
    case msg: CalcModelBase =>
      log.info("记录日志")
      val jobWorkerActor = context.actorOf(Props[JobDistributeActor])
      jobWorkerActor ! msg
      sender() ! "成功接收"

  }

}


class JobDistributeActor extends Actor with ActorLogging  {

  def receive = {
    case msg: CreditCalc =>
      val jobWorkerActor = context.actorOf(Props[CalcCreditJobActor].withDispatcher("akka.actor.credit-blocking-io-dispatcher"))
      jobWorkerActor ! msg
    case msg:AccessCalc =>
      val calcAccessJobActor = context.actorOf(Props[CalcAccessJobActor].withDispatcher("akka.actor.access-blocking-io-dispatcher"))
      calcAccessJobActor ! msg
  }
}

