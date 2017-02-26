package com.hakim.clac.compute

import java.io.File

import akka.actor.{Actor, ActorLogging, Props}
import com.googlecode.scalascriptengine.ScalaScriptEngine
import com.hakim.clac.bootstrap.{AccessCalc, CreditCalc}
import com.hakim.clac.services.{DataCollectorActor, DataRequestSo, DataResponseView}

import scala.collection.immutable.HashMap
import scala.concurrent.Future

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


  var sse: ScalaScriptEngine = null;


  /**
    * 加载所有规则
    */
  override def preStart(): Unit = {
    if(sse==null){
      sse = ScalaScriptEngine.onChangeRefresh(new File("E:\\workspace\\gxb\\data-calc-system\\calc-client\\src\\main\\resources\\script"))
      // delete all compiled classes (i.e. from previous runs)
      sse.deleteAllClassesInOutputDirectory()
      sse.refresh
    }
  }

  /**
    * 初始化状态
    *
    * @return
    */
  def state:CalcState = CalcState("init")

  def receive = {

    case creditCalc: CreditCalc =>
      import akka.pattern.ask

      //收集数据
      log.info("开始收集数据")
      val dataCollectorActor = context.actorOf(Props[DataCollectorActor] )
      val future: Future[DataResponseView] = ask(dataCollectorActor, DataRequestSo).mapTo[DataResponseView]
      future.onSuccess {
        case result : DataResponseView =>{
          log.info("数据收集结束")
          //计算每个分数项
          log.info("开始计算每个项")
          def res:List[Map[String,Object]] = List();
          creditCalc.calcItemIds.foreach(a=>{
            res ++ sse.newInstance[RulesTrait](a).execRule(result);
          })
          log.info("计算项结束")


          log.info("为每项打分start")

          log.info("为每项打分end")


          //TODO发布报告
          //TODO回复报告
          context.self.path
          Thread.sleep(10000)


        }
      }

    case _=>
      log.error("未匹配对应的数据收集情况")
  }



}



trait RulesTrait {
  def execRule(data:DataResponseView): HashMap[String, String] ={
    return HashMap()
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


