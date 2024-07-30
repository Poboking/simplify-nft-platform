// SPDX-License-Identifier: UNLICENSED
pragma solidity 0.4.25;

import "./ERC721Full.sol";

/*
 * @dev: simplify-nft-platform by poboking in 2024/7/16 下午8:30
 */
contract MyColorToken is ERC721Full {
    string[] public colors;
    mapping(string => bool) _colorExists;

    constructor() ERC721Full("Color", "COLOR") public {
    }

    // E.G. color = "#FFFFFF"
    function mint(string memory _color) public {
        require(!_colorExists[_color]);
        uint _id = colors.push(_color);
        _mint(msg.sender, _id);
        _colorExists[_color] = true;
    }

}