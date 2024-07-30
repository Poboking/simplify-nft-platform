package io.knightx.simplifynftplatform.service

import io.knightx.simplifynftplatform.dto.manager.ManagerRegisterReqDto
import io.knightx.simplifynftplatform.dto.manager.ManagerUpdateReqDto
import io.knightx.simplifynftplatform.exception.manager.ManagerAlreadyExistException
import io.knightx.simplifynftplatform.ext.check
import io.knightx.simplifynftplatform.ext.toErrorMsg
import io.knightx.simplifynftplatform.po.Manager
import io.knightx.simplifynftplatform.po.repo.impl.ManagerRepoImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @project: simplify-nft-platform
 * @author: poboking
 * @date: 2024/7/11 下午2:44
 */
@Service
class ManagerService(
    @Autowired
    val repo: ManagerRepoImpl
) {
    fun registerManager(dto: ManagerRegisterReqDto): Boolean {
        if (checkManagerNameExist(dto.managerName)) {
            throw ManagerAlreadyExistException("Member already exists".toErrorMsg())
        }
        return repo.save(dto.toEntity())
    }

    fun getManager(managerId: Long): Manager? {
        checkManagerExist(managerId)
        return repo.lambdaQuery().eq(Manager::getId, managerId).one()
    }


    fun updateManager(dto: ManagerUpdateReqDto): Boolean {
        checkManagerExist(dto.id)
        return repo.lambdaUpdate().eq(Manager::getId, dto.id)
            .set(dto.managerName.check(), Manager::getManagerName, dto.managerName)
            .set(dto.password.check(), Manager::getPassword, dto.password)
            .update()
    }

    fun deleteMember(managerId: Long): Boolean {
        checkManagerExist(managerId)
        return repo.removeById(managerId)
    }

    private fun checkManagerExist(memberId: Long): Boolean {
        return repo.getById(memberId) != null
    }

    private fun checkManagerNameExist(managerName: String): Boolean {
        return repo.lambdaQuery()
            .eq(Manager::getManagerName, managerName)
            .one() != null
    }

    fun checkManager(managerName: String, password: String): Boolean {
        return repo.lambdaQuery()
            .eq(Manager::getManagerName, managerName)
            .eq(Manager::getPassword, password)
            .exists()
    }

    fun getManagerIdByManagerName(name: String): Long {
        return repo.lambdaQuery()
            .eq(Manager::getManagerName, name)
            .one()?.id ?: -1
    }
}