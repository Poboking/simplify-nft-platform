package io.knightx.simplifynftplatform.exception.bcos;

import com.feiniaojin.gracefulresponse.api.ExceptionMapper;

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/17 下午4:49
 */
@ExceptionMapper(code = "600", msg = "Exception: BlockChain Transaction Error, On account of FiscoBcos Unknown Error.")
public class EvmException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final int code;

    public EvmException(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        switch (code) {
            case 0x1:
                return "Unknown";
            case 0x2:
                return "InvalidRLP";
            case 0x3:
                return "InvalidFormat";
            case 0x4:
                return "OutOfGasIntrinsic";
            case 0x5:
                return "InvalidSignature";
            case 0x6:
                return "InvalidNonce";
            case 0x7:
                return "NotEnoughCash";
            case 0x8:
                return "OutOfGasBase";
            case 0x9:
                return "BlockGasLimitReached";
            case 0xa:
                return "BadInstruction";
            case 0xb:
                return "BadJumpDestination";
            case 0xc:
                return "OutOfGas";
            case 0xd:
                return "OutOfStack";
            case 0xe:
                return "StackUnderflow";
            case 0xf:
                return "NonceCheckFail";
            case 0x10:
                return "BlockLimitCheckFail";
            case 0x11:
                return "FilterCheckFail";
            case 0x12:
                return "NoDeployPermission";
            case 0x13:
                return "NoCallPermission";
            case 0x14:
                return "NoTxPermission";
            case 0x15:
                return "PrecompiledError";
            case 0x16:
                return "RevertInstruction";
            case 0x17:
                return "InvalidZeroSignatureFormat";
            case 0x18:
                return "AddressAlreadyUsed";
            case 0x19:
                return "PermissionDenied";
            case 0x1a:
                return "CallAddressError";
            case 0x1b:
                return "GasOverflow";
            case 0x1c:
                return "TxPoolIsFull";
            case 0x1d:
                return "TransactionRefused";
            case 0x1e:
                return "ContractFrozen";
            case 0x1f:
                return "AccountFrozen";
            case 0x2710:
                return "AlreadyKnown";
            case 0x2711:
                return "AlreadyInChain";
            case 0x2712:
                return "InvalidChainId";
            case 0x2713:
                return "InvalidGroupId";
            case 0x2714:
                return "RequestNotBelongToTheGroup";
            case 0x2715:
                return "MalformedTx";
            case 0x2716:
                return "OverGroupMemoryLimit";
            default:
                return "Unknown error code: 0x" + Integer.toHexString(code);
        }
    }
}
