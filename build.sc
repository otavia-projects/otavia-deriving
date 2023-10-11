/*
 * Copyright 2022 Yan Kun <yan_kun_1992@foxmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import mill._
import mill.scalalib._
import mill.scalalib.publish._

object ProjectInfo {

    def description: String     = "A super fast IO & Actor programming model!"
    def organization: String    = "cc.otavia"
    def organizationUrl: String = "https://github.com/otavia-projects"
    def projectUrl: String      = "https://github.com/otavia-projects/otavia-deriving"
    def github                  = VersionControl.github("otavia-projects", "otavia-deriving")
    def repository              = github.browsableRepository.get
    def licenses                = Seq(License.`Apache-2.0`)
    def author                  = Seq("Yan Kun <yan_kun_1992@foxmail.com>")
    def version                 = "0.3.1-SNAPSHOT"
    def scalaVersion            = "3.3.0"
    def scoverageVersion        = "1.4.0"
    def buildTool               = "mill"
    def buildToolVersion        = main.BuildInfo.millVersion

    def core       = ivy"cc.otavia::otavia-runtime:${version}"
    def codec      = ivy"cc.otavia::otavia-codec:${version}"
    def codecHttp  = ivy"cc.otavia::otavia-codec-http:${version}"
    def sql        = ivy"cc.otavia::otavia-sql:${version}"
    def serde      = ivy"cc.otavia::otavia-serde:${version}"
    def serdeJson  = ivy"cc.otavia::otavia-serde-json:${version}"
    def serdeProto = ivy"cc.otavia::otavia-serde-proto:${version}"

    def testDep = ivy"org.scalatest::scalatest:3.2.15"

    def jsoniter      = ivy"com.github.plokhotnyuk.jsoniter-scala::jsoniter-scala-core:2.24.1"
    def jsoniterMacro = ivy"com.github.plokhotnyuk.jsoniter-scala::jsoniter-scala-macros:2.24.1"
    def booPickle     = ivy"io.suzaku::boopickle:1.4.0"
    def netty5        = ivy"io.netty:netty5-codec:5.0.0.Alpha5"
    def xml           = ivy"org.scala-lang.modules::scala-xml:2.1.0"
    def proto         = ivy"io.github.zero-deps::proto:2.1.2"
    def shapeless     = ivy"org.typelevel::shapeless3-deriving:3.3.0"
    def jedis         = ivy"redis.clients:jedis:4.4.3"
    def scram         = ivy"com.ongres.scram:client:2.1"
    def magnolia      = ivy"com.softwaremill.magnolia1_3::magnolia:1.3.3"

}

trait OtaviaModule extends ScalaModule with PublishModule {

    override def scalaVersion = ProjectInfo.scalaVersion

    override def publishVersion: T[String] = ProjectInfo.version

    override def pomSettings: T[PomSettings] = PomSettings(
      description = ProjectInfo.description,
      organization = ProjectInfo.organization,
      url = ProjectInfo.repository,
      licenses = ProjectInfo.licenses,
      versionControl = ProjectInfo.github,
      developers = Seq(
        Developer(
          "yankun1992",
          "Yan Kun",
          "https://github.com/yankun1992",
          Some("otavia-projects"),
          Some("https://github.com/otavia-projects")
        )
      )
    )

    override def scalacOptions = T { scala.Seq("-Yexplicit-nulls") }

}

object `http-macro` extends OtaviaModule {

    override def artifactName = "otavia-http-macro"

    override def ivyDeps = Agg(ProjectInfo.magnolia, ProjectInfo.codecHttp)

    object test extends ScalaTests with TestModule.ScalaTest {

        override def ivyDeps = Agg(ProjectInfo.testDep)

    }

}

object `sql-macro` extends OtaviaModule {

    override def artifactName = "otavia-sql-macro"

    override def ivyDeps = Agg(ProjectInfo.magnolia, ProjectInfo.sql)

    object test extends ScalaTests with TestModule.ScalaTest {

        override def ivyDeps = Agg(ProjectInfo.testDep)

    }

}

object `json-macro` extends OtaviaModule {

    override def artifactName = "otavia-json-macro"

    override def ivyDeps = Agg(ProjectInfo.shapeless, ProjectInfo.magnolia, ProjectInfo.serdeJson)

    object test extends ScalaTests with TestModule.ScalaTest {

        override def ivyDeps = Agg(ProjectInfo.testDep)

    }

}

object `proto-macro` extends OtaviaModule {

    override def artifactName = "otavia-proto-macro"

    override def ivyDeps = Agg(ProjectInfo.serdeProto)

    object test extends ScalaTests with TestModule.ScalaTest {

        override def ivyDeps = Agg(ProjectInfo.testDep)

    }

}
