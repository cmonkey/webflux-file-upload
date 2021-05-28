package org.excavator.boot.webfluxfileupload.controller

import org.springframework.http.codec.multipart.FilePart
import org.springframework.web.bind.annotation.{PostMapping, RequestMapping, RequestPart, RestController}
import reactor.core.publisher.Mono

import java.nio.file.Paths

@RestController
@RequestMapping(Array("upload"))
class UploadController {

  val basePath = Paths.get("./src/main/resources/upload/")

  @PostMapping(Array("file/single"))
  def upload(@RequestPart("user-name") name:String,
            @RequestPart("fileToUpload") filePartMono:Mono[FilePart]): Mono[Void] = {
    println(s"user: $name")

    filePartMono.doOnNext(fp => println(s"Received File:  $fp.filename()"))
      .flatMap(fp => fp.transferTo(basePath.resolve(fp.filename())))
      .`then`()
  }

}
