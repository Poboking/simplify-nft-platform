// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

/*
 * @dev: a20-nft-3_7 by poboking in 2024/4/2 22:11
 */
contract BcosUser{

    uint256 private nonce = 0;

    address public _contractOwner;

    constructor() public {
        _contractOwner = msg.sender; // 将合约部署者设置为合约拥有者
    }

    struct User {
        address userAddress;  // User 地址
        uint256 userId;  // User 唯一 ID
        mapping(uint256 => uint256) holdTokens;  // 持有NFT
        uint256 tokenCount;
    }

    mapping(address => uint256) userAddressToUserId;
    mapping(uint256 => User) users;
    uint256 userCount;

    event UserCreated(address indexed userAddress, uint256 indexed userId);
    event TokenAdded(uint256 indexed userId, uint256 indexed tokenId);
    event TokenDeleted(uint256 indexed userId, uint256 indexed tokenId);
    event TokenTransferred(address indexed from, address indexed to, uint256 indexed tokenId);

    function generateID() public returns (uint256) {
        uint256 id = uint256(keccak256(abi.encodePacked(block.timestamp, nonce)));
        nonce++;
        return id;
    }

    /*
* @dev Throws if the sender is not the owner.
     */
    function _checkOwner() internal view  {
        require(_contractOwner == msg.sender, "OwnableUnauthorizedAccount");
    }

    /**
 * @dev Throws if called by any account other than the owner.
     */
    modifier onlyOwner() {
        _checkOwner();
        _;
    }


    function getUser(uint256 _userId) public view returns (address, uint256, uint256) {
        return (users[_userId].userAddress, users[_userId].userId, users[_userId].tokenCount);
    }

    function getUserToken(uint256 _userId, uint256 _index) public view returns (uint256) {
        return users[_userId].holdTokens[_index];
    }

    function getUserTokenCount(uint256 _userId) public view returns (uint256) {
        return users[_userId].tokenCount;
    }

    function getUserCount() public view returns (uint256) {
        return userCount;
    }

    function getUserAddress(uint256 _userId) public view returns (address) {
        return users[_userId].userAddress;
    }

    function getUserByAddress(address _userAddress) public view returns (uint256) {
        return userAddressToUserId[_userAddress];
    }

    function createUser(address _userAddress) public {
        uint256 userId = generateID();
        User memory newUser = User(_userAddress, userId, 0);
        userAddressToUserId[_userAddress] = userId;
        users[userId] = newUser;
        userCount++;
        emit UserCreated(_userAddress, userId);
    }

    function addToken(uint256 _userId, uint256 _tokenId) public {
        require(users[_userId].userAddress == msg.sender , "You are not the owner of this token");
        //from 1 to x the nft number
        users[_userId].tokenCount++;
        users[_userId].holdTokens[users[_userId].tokenCount] = _tokenId;
        emit TokenAdded(_userId, _tokenId);
    }

    function delToken(uint256 _userId, uint256 _tokenId) public {
        require(users[_userId].userAddress == msg.sender || _contractOwner == msg.sender, "You are not the owner of this token");
        for (uint256 i = 1; i <= users[_userId].tokenCount; i++) {
            if (users[_userId].holdTokens[i] == _tokenId) {
                users[_userId].holdTokens[i] = 0;
                users[_userId].tokenCount--;
                emit TokenDeleted(_userId, _tokenId);
                break;
            }
        }
    }

    function transferToken(address _from, address _to, uint256 _tokenId) public {
        uint256 fromUserId = 0;
        uint256 toUserId = 0;
        fromUserId = getUserByAddress(_from);
        toUserId = getUserByAddress(_to);
        require(fromUserId != 0, "From user does not exist");
        require(toUserId != 0, "To user does not exist");
        delToken(fromUserId, _tokenId);
        addToken(toUserId, _tokenId);
        emit TokenTransferred(_from, _to, _tokenId);
    }

    function initToken(address _to, uint256 _tokenId) public {
        uint256 toUserId = 0;
        toUserId = getUserByAddress(_to);
        require(toUserId != 0, "To user does not exist");
        addToken(toUserId, _tokenId);
        emit TokenTransferred(address(0), _to, _tokenId);
    }
}

