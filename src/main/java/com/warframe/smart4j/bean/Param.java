package com.warframe.smart4j.bean;

import com.warframe.smart4j.util.CastUtil;
import com.warframe.smart4j.util.CollectionUtil;
import com.warframe.smart4j.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author warframe[github.com/WarframePrimer]
 * @Date 2017/10/15 16:27
 * <p>
 * 请求参数对象
 * <p>
 * 进行重构，对于Param中的属性，原来是paramMap(参数名---参数值)改为更加可控,其实就是管理的更加细致
 * ---formParamList 表单参数列表(List<FormParam>)
 * ---fileParamList 文件参数列表(List<FileParam>)
 * 在一个表单中，所有的参数可以分为两类：表单参数和文件参数。
 */
public class Param {

    private List<FormParam> formParamList;
    private List<FileParam> fileParamList;

//    private Map<String,Object> paramMap;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

//    public Param(Map<String, Object> paramMap) {
//        this.paramMap = paramMap;
//    }

    /**
     * 根据参数名获取long型参数值
     */
//    public long getLong(String name){
//        return CastUtil.castLong(paramMap.get(name));
//    }
//
//    /**
//     * 获取所有字段信息
//     */
//    public Map<String,Object> getMap(){
//        return paramMap;
//    }
//
//
//    /**
//     * 验证参数是否为空
//     */
//    public boolean isEmpty(){
//        return CollectionUtil.isEmpty(paramMap);
//    }

    /**
     * 获取请求参数映射
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(formParamList)) {
            for (FormParam formParam : formParamList) {
                String fieldName = formParam.getFieldName();
                Object fieldValue = formParam.getFieldValue();
                if (fieldMap.containsKey(fieldName)) {
                    fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
                }
                fieldMap.put(fieldName, fieldValue);
            }
        }
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(fileParamList)) {
            for (FileParam fileParam : fileParamList) {
                String fieldName = fileParam.getFieldName();
                List<FileParam> fileParamList;
                if (fileMap.containsKey(fieldName)) {
                    fileParamList = fileMap.get(fieldName);
                } else {
                    fileParamList = new ArrayList<>();
                }
                fileParamList.add(fileParam);
                fileMap.put(fieldName, fileParamList);
            }
        }
        return fileMap;
    }

    /**
     * 获取所有上传文件
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtil.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }


    /**
     * 验证参数是否为空
     */
    public boolean isEmpty() {
        //TODO
        return true;
    }


    /**
     * 根据参数名获取String参数值
     */
    public String getString(String fieldName){
        return CastUtil.castString(getFieldMap().get(fieldName));
    }

    /**
     * 根据参数名获取double参数值
     */
    public double getDouble(String fieldName){
        return CastUtil.castDouble(getFieldMap().get(fieldName));
    }

    /**
     * 根据参数名获取long参数值
     */
    public long getLong(String fieldName){
        return CastUtil.castLong(getFieldMap().get(fieldName));
    }

    /**
     * 根据参数名获取int参数值
     */
    public int getInt(String fieldName){
        return CastUtil.castInt(getFieldMap().get(fieldName));
    }

    /**
     * 根据参数名获取boolean参数值
     */
    public boolean getBoolean(String fieldName){
        return CastUtil.castBoolean(getFieldMap().get(fieldName));
    }
}

