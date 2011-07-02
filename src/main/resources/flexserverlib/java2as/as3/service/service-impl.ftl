package ${model.packageName} {

    <#if model.importsFragment?length gt 0>
	${model.importsFragment}
    </#if>

	public class ${model.simpleName} ${model.polymorphicsFragment} {

		<#if model.methods?size gt 0>
		<#list model.methods as meth>
		public function ${meth.name}():${meth.returnType.simpleName} {
            
        }

		</#list>
		</#if>
	}

}
