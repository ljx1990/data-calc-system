package com.hakim.clac.services

import java.io.File

import akka.actor.{Actor, ActorLogging}
import com.googlecode.scalascriptengine.ScalaScriptEngine
import com.hakim.clac.compute.CalcState

import scala.collection.immutable.HashMap

case class UserInfoSo(phone:String,email:String,uuid:String)
case class DataRequestSo(dataIds:List[String],userInfoSo: UserInfoSo)

/**
  * 数据收集actor
  * 由于不同的信用分收集的数据不一，于是通过dataId原子化需要收集的数据
  */
class DataCollectorActor extends Actor with  ActorLogging{

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
    case dataRequestSo: DataRequestSo =>
      //收集数据
      def data:HashMap[String,String] = HashMap();

      //计算每个分数项
      def res:List[Map[String,Object]] = List();
      dataRequestSo.dataIds.foreach(a=>{
        res ++ sse.newInstance[RulesTrait](a).execRule(data);
      })

      //发布报告
      //回复报告
      context.self.path
      Thread.sleep(10000)

    case _=>
      log.error("未匹配对应的数据收集情况")
  }



}



trait RulesTrait {
  def execRule(data:HashMap[String,String]): HashMap[String, String] ={
    return HashMap()
  }
}