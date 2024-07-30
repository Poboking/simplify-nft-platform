// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

contract BcosNFT {
    
    address public chainId = address(this);
    
    address public contractAddress;
    
    address public _contractOwner;

    uint256 private nonce = 0;
 
    constructor() public{
        _contractOwner = msg.sender; // 将合约部署者设置为合约拥有者
    }

    // 定义结构体表示 NFT
    struct Token {
        address creator;  // NFT 创建者地址
        address owner;    // NFT 拥有者地址
        string name;      // NFT 名称
        string tokenURI;  // NFT Data
        uint256 tokenId;  // NFT 唯一 ID
        uint256 serialNumber;  // NFT 序列号
        uint256 quantity; //发行数量
    }

    // 通过 tokenId 获取 NFT
    mapping(uint256 => Token) tokens;
    mapping(string => uint256) keyToId;
    mapping(string => uint256) tokenURIToTokenId;
    mapping(string => uint256) stringToUint;
    uint256 tokenLength;

    // 事件通知 NFT 发行
    event NFTCreated(uint256 indexed tokenId, address indexed creator, address indexed owner, string name, uint256 serialNumber, uint256 quantity);

    // 事件通知 NFT交易
    event NFTTransfer(uint256 indexed tokenId, address indexed from, address indexed to);

    function generateID() public returns (uint256) {
        uint256 id = uint256(keccak256(abi.encodePacked(block.timestamp, nonce)));
        nonce++;
        return id;
    }

    function generateKey(string memory key, uint256 num) public pure returns (string) {
        return string(abi.encodePacked(key, num));
    }

    function stringToUniqueUint(string memory input) public returns (uint256) {
        require(bytes(input).length > 0, "Input string must not be empty");
        uint256 hashed = uint256(keccak256(abi.encodePacked(input)));
        // 如果已经存在相同的字符串，则重新计算哈希直到得到不重复的值
        while (stringToUint[input] != 0 && stringToUint[input] != hashed) {
            input = concatenate(input, uintToString(hashed)); // 在输入字符串后附加哈希值
            hashed = uint256(keccak256(abi.encodePacked(input)));}
        stringToUint[input] = hashed;
        return hashed;}

    // 辅助函数：将 uint256 转换为字符串
    function uintToString(uint256 value) internal pure returns (string memory) {
        if (value == 0) {
            return "0";}
        uint256 temp = value;
        uint256 digits;
        while (temp != 0) {
            digits++;
            temp /= 10;}
        bytes memory buffer = new bytes(digits);
        while (value != 0) {
            digits -= 1;
            buffer[digits] = bytes1(uint8(48 + uint256(value % 10)));
            value /= 10;}
        return string(buffer);}

    // 辅助函数：连接两个字符串
    function concatenate(string memory a, string memory b) internal pure returns (string memory) {
        return string(abi.encodePacked(a, b));
    }

/*
* @dev Throws if the sender is not the owner.
     */
    function _checkOwner() internal view {
        require(_contractOwner == msg.sender, "OwnableUnauthorizedAccount");
    }

/**
* @dev Throws if called by any account other than the owner.
     */
    modifier onlyOwner() {
        _checkOwner();
        _;
    }

// 修饰符：限制只有 NFT 拥有者或经过授权的地址可以调用
    modifier isApprovedOrOwner(uint256 _tokenId) {
        require(_tokenId > 0, "Invalid token ID");
        require(tokens[_tokenId].creator != address(0), "Token does not exist");
        require(tokens[_tokenId].owner == msg.sender || msg.sender == address(this),
            "You are not approved or the owner of this token");
        _;
    }

// 发行指定数量新的 NFT
    function createNFT(address creatorAddress, string _name, string baseURI, uint256 quantity) public  {
        string memory baseKey = _name;
        for (uint256 i = 0; i < quantity; i++) {
            uint256 tokenId = generateID();
            Token memory newToken = Token(creatorAddress, _contractOwner, _name, baseURI, tokenId, i, quantity);
            tokenURIToTokenId[baseURI] = tokenId;
            tokens[tokenId] = newToken;
            tokenLength++;
            keyToId[generateKey(baseKey, i + 1)] = tokenId;
            emit NFTCreated(tokenId, msg.sender, _contractOwner, _name, i + 1, quantity);
        }
    }

//发行指定序列号的NFT
    function createNFT(address creatorAddress, string _name, string baseURI, uint256 serialNumber, uint256 quantity) public returns (uint256){
        require(serialNumber > 0 && serialNumber <= quantity, "Invalid serial number");
        require(keyToId[generateKey(_name, serialNumber)] == 0, "Token already exists");
        uint256 tokenId = generateID();
        Token memory newToken = Token(creatorAddress, _contractOwner, _name, baseURI, tokenId, serialNumber, quantity);
        tokens[tokenId] = newToken;
        tokenURIToTokenId[baseURI] = tokenId;
        keyToId[generateKey(_name, serialNumber)] = tokenId;
        tokenLength++;
        emit NFTCreated(tokenId, msg.sender, _contractOwner, _name, serialNumber, quantity);
        return tokenId;
    }

// 交易 NFT
    function transferNFT(uint256 _tokenId, address _to) public isApprovedOrOwner(_tokenId) {
        require(tokens[_tokenId].owner == msg.sender, "You are not the owner of this token");
        address _from = tokens[_tokenId].owner;
        tokens[_tokenId].owner = _to;
        emit NFTTransfer(_tokenId, _from, _to);
    }

// 获取指定 NFT 的信息
    function getNFT(uint256 _tokenId) public view returns (address, address, string memory, uint256) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return (tokens[_tokenId].creator, tokens[_tokenId].owner, tokens[_tokenId].name, tokens[_tokenId].tokenId);
    }

    function getNFTCreator(uint256 _tokenId) public view returns (address) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].creator;
    }

    function getNFTName(uint256 _tokenId) public view returns (string) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].name;
    }

    function getNFTOwner(uint256 _tokenId) public view returns (address) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].owner;
    }

    function getNFTTokenURI(uint256 _tokenId) public view returns (string) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].tokenURI;
    }

    function getNFTSerialNumber(uint256 _tokenId) public view returns (uint256)  {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].serialNumber;
    }

    function getNFTQuantity(uint256 _tokenId) public view returns (uint256) {
        require(_tokenId > 0 && tokens[_tokenId].creator != address(0), "Invalid token ID");
        return tokens[_tokenId].quantity;
    }

//这里根据IssuedCollectionID充当tokenURI
    function getTokenIdByTokenURI(string _tokenURI) public view returns (uint256) {
        return tokenURIToTokenId[_tokenURI];
    }

    function getTokenIdsByName(string _name) public view returns (uint256[]) {
        string memory baseKey = _name;
        uint256 firstId = keyToId[generateKey(baseKey, 1)];
        Token memory token = tokens[firstId];
        if (token.quantity == 0) {
            return new uint256[](0);
        }
        uint256[] memory tokenIds = new uint256[](token.quantity);
        for (uint256 i = 0; i < token.quantity; i++) {
            uint256 tokenId = keyToId[generateKey(baseKey, i + 1)];
            if (tokenId == 0) {
                break;
            }
            tokenIds[i] = (tokenId);
        }
        return tokenIds;
    }

// 从1开始, 通过名称和序列号获取 NFT 的唯一 ID
    function getTokenIdByNameAndSerialNumber(string _name, uint256 serialNumber) public view returns (uint256) {
        return keyToId[generateKey(_name, serialNumber)];
    }

}
