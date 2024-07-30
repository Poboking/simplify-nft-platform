package io.knightx.simplifynftplatform.po.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import io.knightx.simplifynftplatform.po.HoldCollection;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author NaGaYa
 * @description 针对表【hold_collection】的多表联查数据库操作Mapper
 * @createDate 2024-07-11 14:40:40
 * @Entity io.knightx.simplifynftplatform.po.HoldCollection
 */
@Mapper
public interface MPJHoldCollectionMapper extends MPJBaseMapper<HoldCollection> {
}
