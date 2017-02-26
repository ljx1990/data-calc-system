package com.hakim.clac.services

import java.io.File

import akka.actor.{Actor, ActorLogging}
import com.googlecode.scalascriptengine.ScalaScriptEngine
import com.hakim.clac.compute.CalcState

import scala.collection.immutable.HashMap

case class UserInfoSo(phone:String,email:String,uuid:String)
case class DataRequestSo(dataIds:List[String],userInfoSo: UserInfoSo)
case class DataResponseView(data:Map[String,Object]);


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
      def res:Map[String,Map[String,Object]] = HashMap();
      dataRequestSo.dataIds.foreach(a=>{
        res  += (a -> sse.newInstance[DataCollectTrait](a).collectData(dataRequestSo));
      })
      sender() ! DataResponseView(res)
    case _=>
      log.error("未匹配对应的数据收集情况")
  }

}


/**
  * 数据收集特性
  */
trait DataCollectTrait {
  def collectData(data:DataRequestSo): HashMap[String, String] ={
    return HashMap()
  }
}