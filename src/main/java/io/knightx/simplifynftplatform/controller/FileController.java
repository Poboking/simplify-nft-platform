package io.knightx.simplifynftplatform.controller;

import io.knightx.simplifynftplatform.annotation.CheckUserLogin;
import io.knightx.simplifynftplatform.bcos.service.ipfs.IPFSService;
import io.knightx.simplifynftplatform.dto.ipfs.SaveRespDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/30 下午3:48
 */
@RestController
@CheckUserLogin
@RequestMapping("/file")
public class FileController {
    private final IPFSService ipfsService;

    @Autowired
    public FileController(IPFSService ipfsService) {
        this.ipfsService = ipfsService;
    }

    @PostMapping("/upload")
    public SaveRespDto save(@RequestParam("file") MultipartFile file) throws IOException {
        String url = ipfsService.save(file);
        return new SaveRespDto(url);
    }
}
