package io.choerodon.admin.app.service;

import java.util.List;
import java.util.Map;

import io.choerodon.admin.api.dto.CiCdPipelineRecordVO;
import io.choerodon.admin.api.dto.MenuClickDTO;


/**
 * @author superlee
 */
public interface StatisticService {
    void saveMenuClick(List<MenuClickDTO> menuClickList);

    Map<String, Object> queryMenuClick(String beginDate, String endDate, String code);

    void auidt(CiCdPipelineRecordVO devopsPipelineVO);
}
