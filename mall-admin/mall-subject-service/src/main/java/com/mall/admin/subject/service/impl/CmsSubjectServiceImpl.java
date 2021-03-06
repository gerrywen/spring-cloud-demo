package com.mall.admin.subject.service.impl;

import com.github.pagehelper.PageHelper;
import com.mall.admin.mapper.CmsSubjectMapper;
import com.mall.admin.model.CmsSubject;
import com.mall.admin.model.CmsSubjectExample;
import com.mall.admin.model.CmsSubjectProductRelation;
import com.mall.admin.subject.dao.CmsSubjectProductRelationDao;
import com.mall.admin.subject.service.CmsSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 商品专题Service实现类
 * Created by macro on 2018/6/1.
 */
@Service
public class CmsSubjectServiceImpl implements CmsSubjectService {
    @Autowired
    private CmsSubjectMapper subjectMapper;

    @Autowired
    private CmsSubjectProductRelationDao subjectProductRelationDao;

    @Override
    public List<CmsSubject> listAll() {
        return subjectMapper.selectByExample(new CmsSubjectExample());
    }

    @Override
    public List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        CmsSubjectExample example = new CmsSubjectExample();
        CmsSubjectExample.Criteria criteria = example.createCriteria();
        if (!StringUtils.isEmpty(keyword)) {
            criteria.andTitleLike("%" + keyword + "%");
        }
        return subjectMapper.selectByExample(example);
    }

    @Override
    public int insertList(List<CmsSubjectProductRelation> subjectProductRelationList) {
        return subjectProductRelationDao.insertList(subjectProductRelationList);
    }
}
