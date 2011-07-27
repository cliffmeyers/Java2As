package ${model.packageName} {

    <#if model.importsFragment?length gt 0>
	${model.importsFragment}
    
    </#if>
	[Bindable]
	public class ${model.simpleName}Base ${model.polymorphicsFragment} {

		<#if model.getProperties()?size gt 0>
		// Fields

		<#list model.getProperties() as prop>
		private var _${prop.getName()}:${prop.getTypeName()};

		</#list>

		// Getters / Setters

		<#list model.getProperties() as prop>
		<#if prop.hasMetadata>${prop.metadata}</#if>
		public function get ${prop.getName()}():${prop.getTypeName()} {
			return _${prop.getName()};
		}
		public function set ${prop.getName()}(value:${prop.getTypeName()}):void {
			_${prop.getName()} = value;
		}
		
		</#list>
		</#if>
	}

}
