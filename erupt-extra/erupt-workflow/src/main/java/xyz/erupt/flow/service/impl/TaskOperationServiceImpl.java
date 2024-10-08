package xyz.erupt.flow.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.erupt.flow.bean.entity.OaTask;
import xyz.erupt.flow.bean.entity.OaTaskOperation;
import xyz.erupt.flow.service.TaskOperationService;
import xyz.erupt.jpa.dao.EruptDao;
import xyz.erupt.upms.model.EruptUser;
import xyz.erupt.upms.service.EruptUserService;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TaskOperationServiceImpl implements TaskOperationService {

    @Resource
    private EruptUserService eruptUserService;

    @Resource
    private EruptDao eruptDao;

    @Override
    @Transactional
    public void log(OaTask task, String operation, String remarks) {
        EruptUser currentEruptUser = eruptUserService.getCurrentEruptUser();
        OaTaskOperation build = OaTaskOperation.builder()
                .processInstId(task.getProcessInstId())
                .processDefId(task.getProcessDefId())
                .taskId(task.getId())
                .taskName(task.getTaskName())
                .operator(currentEruptUser.getAccount())
                .operatorName(currentEruptUser.getName())
                .operation(operation)
                .remarks(remarks)
                .operationDate(new Date())
                .build();
        eruptDao.persist(build);
    }

    @Override
    @Transactional
    public void log(OaTask task, String operation, String remarks, String nodeId, String nodeName) {
        EruptUser currentEruptUser = eruptUserService.getCurrentEruptUser();
        OaTaskOperation build = OaTaskOperation.builder()
                .processInstId(task.getProcessInstId())
                .processDefId(task.getProcessDefId())
                .taskId(task.getId())
                .taskName(task.getTaskName())
                .operator(currentEruptUser.getAccount())
                .operatorName(currentEruptUser.getName())
                .operation(operation)
                .remarks(remarks)
                .operationDate(new Date())
                .targetNodeId(nodeId)
                .targetNodeName(nodeName)
                .build();
        eruptDao.persist(build);
    }

    @Override
    public List<OaTaskOperation> listByOperator(String account) {
        return eruptDao.lambdaQuery(OaTaskOperation.class).eq(OaTaskOperation::getOperator, account)
                .orderByDesc(OaTaskOperation::getOperationDate).list();
    }
}
