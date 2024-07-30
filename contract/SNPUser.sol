// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

/*
 * @dev: simplify-nft-platform by poboking in 2024/7/16 下午9:03
 */

contract SNPUser {
    mapping(uint256 => address) public nameToAddress;

    event register(string indexed name, address indexed account);

    function defaultRegisterUser(string memory name, address account) public {
        uint256 id = toSha256(name);
        nameToAddress[id] = account;
        emit register(name, account);
    }

    function registerUser(string memory name) public{
        defaultRegisterUser(name, msg.sender);
    }

    function getAddress(string memory name) public view returns (address) {
        uint256 id = toSha256(name);
        return nameToAddress[id];
    }

    function getRandomAddress(string memory name) view public returns (address) {
        return address(uint160(uint256(keccak256(abi.encodePacked(name, block.timestamp)))));
    }

    function toSha256(string memory input) internal pure returns (uint256) {
        bytes32 hash = keccak256(abi.encodePacked(input));
        return uint256(hash);
    }
}

