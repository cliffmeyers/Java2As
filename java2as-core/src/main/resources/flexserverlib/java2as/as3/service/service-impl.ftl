package ${model.packageName} {

    <#if model.importsFragment?length gt 0>
	${model.importsFragment}
    </#if>

	public class ${model.simpleName} ${model.polymorphicsFragment} {

		<#if model.methods?size gt 0>
		<#list model.methods as method>
        /**
         * returns ${method.returnType.qualifiedName}
         */
		public function ${method.name}(${method.parameterList}):${method.returnType.simpleName} {
            return getOperation("${method.name}").send(${method.parameterNameList});
        }

		</#list>
		</#if>
	}

}
