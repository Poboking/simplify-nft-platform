// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "./ERC721Full.sol";

/*
 * @dev: simplify-nft-platform by poboking in 2024/7/16 下午9:22
 */
contract SNPToken is ERC721Full {
    event tokenMint(address indexed creator, uint256 indexed tokenId, string indexed tokenUrl);
    constructor(string memory name, string memory symbol) public ERC721Full(name, symbol) {}

    function mint(string memory tokenUrl) public returns (uint256){
        uint256 _id = keccak256Hash(tokenUrl);
        _mint(msg.sender, _id);
        _setTokenURI(_id, tokenUrl);
        emit tokenMint(msg.sender, _id, tokenUrl);
        return _id;
    }

    function burn(uint256 tokenId) public {
        _burn(msg.sender, tokenId);
    }

    function keccak256Hash(string memory _input) public pure returns (uint256) {
        bytes memory inputBytes = bytes(_input);
        bytes32 hash = keccak256(inputBytes);
        return uint256(hash);
    }
}
