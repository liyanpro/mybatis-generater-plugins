package cloud.liyan.mybatis.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

/**
 * @author liyan
 * @description
 * @date 2019-08-27 20:44
 */
public class MySqlLimitPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    /**
     * 为每个Example类添加limit和offset属性已经set、get方法
     */
    @Override
    public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {

        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        Field limit = new Field();
        limit.setName("limit");
        limit.setVisibility(JavaVisibility.PRIVATE);
        limit.setType(integerWrapper);
        topLevelClass.addField(limit);

        Method setLimit = new Method();
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.setName("setLimit");
        setLimit.addParameter(new Parameter(integerWrapper, "limit"));
        setLimit.addBodyLine("this.limit = limit;");
        topLevelClass.addMethod(setLimit);

        Method getLimit = new Method();
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerWrapper);
        getLimit.setName("getLimit");
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);

        Field offset = new Field();
        offset.setName("offset");
        offset.setVisibility(JavaVisibility.PRIVATE);
        offset.setType(integerWrapper);
        topLevelClass.addField(offset);

        Method setOffset = new Method();
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.setName("setOffset");
        setOffset.addParameter(new Parameter(integerWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);

        Method getOffset = new Method();
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerWrapper);
        getOffset.setName("getOffset");
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);

        return true;
    }
    @Override
    public boolean modelRecordWithBLOBsClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        PrimitiveTypeWrapper integerWrapper = FullyQualifiedJavaType.getIntInstance().getPrimitiveTypeWrapper();

        Field limit = new Field();
        limit.setName("limit");
        limit.setVisibility(JavaVisibility.PRIVATE);
        limit.setType(integerWrapper);
        topLevelClass.addField(limit);

        Method setLimit = new Method();
        setLimit.setVisibility(JavaVisibility.PUBLIC);
        setLimit.setName("setLimit");
        setLimit.addParameter(new Parameter(integerWrapper, "limit"));
        setLimit.addBodyLine("this.limit = limit;");
        topLevelClass.addMethod(setLimit);

        Method getLimit = new Method();
        getLimit.setVisibility(JavaVisibility.PUBLIC);
        getLimit.setReturnType(integerWrapper);
        getLimit.setName("getLimit");
        getLimit.addBodyLine("return limit;");
        topLevelClass.addMethod(getLimit);

        Field offset = new Field();
        offset.setName("offset");
        offset.setVisibility(JavaVisibility.PRIVATE);
        offset.setType(integerWrapper);
        topLevelClass.addField(offset);

        Method setOffset = new Method();
        setOffset.setVisibility(JavaVisibility.PUBLIC);
        setOffset.setName("setOffset");
        setOffset.addParameter(new Parameter(integerWrapper, "offset"));
        setOffset.addBodyLine("this.offset = offset;");
        topLevelClass.addMethod(setOffset);

        Method getOffset = new Method();
        getOffset.setVisibility(JavaVisibility.PUBLIC);
        getOffset.setReturnType(integerWrapper);
        getOffset.setName("getOffset");
        getOffset.addBodyLine("return offset;");
        topLevelClass.addMethod(getOffset);
        return true;
    }

    /**
     * 为Mapper.xml的selectByExample添加limit
     */
    @Override
    public boolean sqlMapSelectByExampleWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

        XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "offset != null"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${offset}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "offset == null"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);

        return true;
    }
    @Override
    public boolean sqlMapSelectByExampleWithBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

        XmlElement ifLimitNotNullElement = new XmlElement("if");
        ifLimitNotNullElement.addAttribute(new Attribute("test", "limit != null"));

        XmlElement ifOffsetNotNullElement = new XmlElement("if");
        ifOffsetNotNullElement.addAttribute(new Attribute("test", "offset != null"));
        ifOffsetNotNullElement.addElement(new TextElement("limit ${offset}, ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNotNullElement);

        XmlElement ifOffsetNullElement = new XmlElement("if");
        ifOffsetNullElement.addAttribute(new Attribute("test", "offset == null"));
        ifOffsetNullElement.addElement(new TextElement("limit ${limit}"));
        ifLimitNotNullElement.addElement(ifOffsetNullElement);

        element.addElement(ifLimitNotNullElement);
        return true;
    }

    private static final String USEGENERATEDKEYS = "useGeneratedKeys";
    private static final String KEYCOLUMN = "keyColumn";
    private static final String KEYPROPERTY = "keyProperty";
    private static final String IS_GEN_USEGENERATEDKEYS = "my.isgen.usekeys";

    /**
     * 该方法在每一个mapper.xml文件的insert节点生成是调用，我们要做的就是判断是否要插入三个属性，
     * 如果需要插入，就往XmlElement添加三个元素即可；
     */
    @Override
    public boolean sqlMapInsertSelectiveElementGenerated(XmlElement element,
                                                         IntrospectedTable introspectedTable) {
        //定义是否需要生成三个属性
        boolean isGen = true;
        //调用getTableConfigurationProperty方法，得到在当前这个table中定义的property元素
        //直接去尝试获取my.isgen.usekeys property；
//	    String isGenStr = introspectedTable
//	            .getTableConfigurationProperty(IS_GEN_USEGENERATEDKEYS);
//	    //如果得到了值，就尝试转化成boolean
//	    if (StringUtils.stringHasValue(isGenStr)) {
//	        isGen = Boolean.valueOf(isGenStr);
//	    }
        //如果需要生成参数
        if (isGen) {
            // 要使用usegeneratedkeys只能有一个主键，并且主键的类型必须是数字类型；
            //通过introspectedTable的getPrimaryKeyColumns方法得到解析出来数据库中的主键列；
            //因为主键列可能是多个，所以返回的是List<IntrospectedColumn>
            List<IntrospectedColumn> keyColumns = introspectedTable
                    .getPrimaryKeyColumns();
            IntrospectedColumn keyColumn = null;
            //对于usegeneratedkeys来说，只能有一个主键列；
            if (keyColumns.size() == 1) {
                //得到这个唯一的主键列
                keyColumn = keyColumns.get(0);
                //得到这个列映射成Java模型之后的属性对应的Java类型；
                FullyQualifiedJavaType javaType = keyColumn
                        .getFullyQualifiedJavaType();
                //usegeneratedkeys要求主键只能是递增的，所以我们把这个主键属性的类型分别和Integer，Long，Short做对比；
                if (javaType.equals(PrimitiveTypeWrapper.getIntegerInstance())
                        || javaType.equals(PrimitiveTypeWrapper
                        .getLongInstance())
                        || javaType.equals(PrimitiveTypeWrapper
                        .getShortInstance())) {
                    //如果是Integer，Long，Short三个类型中的而一个；则添加属性；
                    //因为我们要添加的属性就是insert元素上的，而insert元素就是根节点，所以element就是insert元素；
                    element.addAttribute(new Attribute(USEGENERATEDKEYS, "true"));
                    //通过IntrospectedColumn的getActualColumnName得到列中的名称，用于生成keyColumn属性；
                    element.addAttribute(new Attribute(KEYCOLUMN, keyColumn
                            .getActualColumnName()));
                    //通过IntrospectedColumn的getJavaProperty方法得到key在Java对象中的属性名，用于生成keyProperty属性
                    element.addAttribute(new Attribute(KEYPROPERTY, keyColumn
                            .getJavaProperty()));
                }
            }
        }
        return true;
    }
}
