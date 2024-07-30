// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "./HelloWorld.sol";

/*
 * @dev: simplify-nft-platform by poboking in 2024/7/16 下午8:07
 */
contract TestImport {
    address public chainId = address(this);
    string public contractOwnerName;
    constructor(string name){
        contractOwnerName = name;
    }
    
    function sayHello() view returns (string) {
        return "test";
    }
}
