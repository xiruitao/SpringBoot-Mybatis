package com.miaosha.dao;

import com.miaosha.dataobject.SequenceInfoDO;

public interface SequenceInfoDOMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    int deleteByPrimaryKey(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    int insert(SequenceInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    int insertSelective(SequenceInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    SequenceInfoDO selectByPrimaryKey(String name);

    SequenceInfoDO getSequenceByName(String name);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    int updateByPrimaryKeySelective(SequenceInfoDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sequence_info
     *
     * @mbg.generated Fri Feb 08 19:29:50 CST 2019
     */
    int updateByPrimaryKey(SequenceInfoDO record);
}