package ${model.packageName} {

    <#if model.importsFragment?length gt 0>
	${model.importsFragment}
    </#if>

	public interface I${model.simpleName} {

		<#if model.methods?size gt 0>
		<#list model.methods as meth>
        /*
         * returns ${meth.returnType.qualifiedName}
         */
		function ${meth.name}():AsyncToken;

		</#list>
		</#if>
	}

}
