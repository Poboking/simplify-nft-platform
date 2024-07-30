package io.knightx.simplifynftplatform.bcos.service.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import io.ipfs.multihash.Multihash;
import io.knightx.simplifynftplatform.dto.ipfs.TokenMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午9:48
 */
@Component
public class IPFSService {

    private final IPFS ipfs;

    @Autowired
    public IPFSService(IPFS ipfs) {
        this.ipfs = ipfs;
    }

    public String save(String name, String creator, String timestamp, String data) throws IOException {
        TokenMetadata metadata = TokenMetadata.Companion.builder()
                .name(name)
                .create(creator)
                .timestamp(timestamp)
                .data(data)
                .build();
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable
                .ByteArrayWrapper("metadata.json", metadata.toJson().getBytes());
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toBase58();
    }

    public String save(MultipartFile metadata) throws IOException {
        NamedStreamable.ByteArrayWrapper file = new NamedStreamable
                .ByteArrayWrapper("metadata.file", metadata.getBytes());
        MerkleNode addResult = ipfs.add(file).get(0);
        return addResult.hash.toBase58();
    }

    public String getJson(String link) throws IOException {
        Multihash hash = Multihash.fromBase58(link);
        byte[] resultBytes = ipfs.cat(hash);
        return byteToString(resultBytes);
    }

    public TokenMetadata getMetadata(String link) throws IOException {
        return TokenMetadata.Companion.fromJson(getJson(link));
    }

    public static String byteToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
