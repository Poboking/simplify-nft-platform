// SPDX-License-Identifier: UNLICENSED
pragma solidity ^0.4.25;

import "./IERC165.sol";

/*
 * @dev: simplify-nft-platform by poboking in 2024/7/13 下午3:09
 */
abstract contract ERC165 is IERC165 {
    /**
     * @dev See {IERC165-supportsInterface}.
     */
    function supportsInterface(bytes4 interfaceId) public view virtual returns (bool) {
        return interfaceId == type(IERC165).interfaceId;
    }
}