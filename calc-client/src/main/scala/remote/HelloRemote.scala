package remote

import akka.actor._

object HelloRemote extends App  {
  val system = ActorSystem("HelloRemoteSystem")

  val remoteActor = system.actorOf(Props[WorkerDispatcherActor], name = "WorkerDispatcherActor")

  remoteActor ! "The RemoteActor is alive1"
  remoteActor ! "The RemoteActor is alive2"
  remoteActor ! "The RemoteActor is alive3"
  remoteActor ! "The RemoteActor is alive4"
  remoteActor ! "The RemoteActor is alive5"
  remoteActor ! "The RemoteActor is alive6"

}

class WorkerDispatcherActor extends Actor with ActorLogging  {
  def receive = {
    case msg: String =>
      val jobWorkerActor = context.actorOf(Props[JobWorkerActor].withDispatcher("akka.actor.blocking-io-dispatcher"))
      jobWorkerActor ! "222"
      log.info(s"RemoteActor received message '$msg'"+context.self.path)

    case killJobWorker =>
      val jobwoker = context.actorSelection("...")
      jobwoker ! PoisonPill
  }

}


class JobWorkerActor extends Actor with ActorLogging {

  def receive = {
    case msg: String =>
      context.self.path
      log.info(s"RemoteActor received message '$msg' "+context.self.path)
      Thread.sleep(10000)
     /* val localSender = context.actorSelection("akka.tcp://LocalSystem@127.0.0.1:2552/user/LocalActor")
      localSender ! "Hello from the RemoteActor"*/
  }
}


