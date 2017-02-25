package com.hakim.clac.compute

import akka.actor.{Actor, ActorLogging}
import com.hakim.clac.bootstrap.{AccessCalc, CreditCalc}

/**
  * Created by Administrator on 2017/2/25 0025.
  */
class CalcScoreActor {

}

case class CalcState(state:String);
/**
  * 计算信用分的actor
  */
class CalcCreditJobActor extends Actor with ActorLogging {

  /**
    * 初始化状态
    * @return
    */
  def state:CalcState = CalcState("init")

  def receive = {
    case msg: CreditCalc =>
      //收集数据
      //计算评分
      //发布报告
      //回复报告
      context.self.path
      log.info(s"RemoteActor received message '$msg' "+context.self.path)
      Thread.sleep(10000)
  }
}



/**
  * 计算准入评分actor
  */
class CalcAccessJobActor extends Actor with ActorLogging {

  /**
    * 初始化状态
    * @return
    */
  def state:CalcState = CalcState("init")

  def receive = {
    case msg: AccessCalc =>
      //收集数据
      //计算评分
      //发布报告
      //回复报告
      context.self.path
      log.info(s"RemoteActor received message '$msg' "+context.self.path)
  }
}


