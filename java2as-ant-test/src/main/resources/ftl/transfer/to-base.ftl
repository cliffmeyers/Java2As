// custom template

package ${model.getPackageName()} {

    <#if model.importsFragment?length gt 0>
	${model.getImportsFragment()}
    
    </#if>
	[Bindable]
	public class ${model.getSimpleName()}Base ${model.getPolymorphicsFragment()} {

		<#if model.getProperties()?size gt 0>
		// Fields

		<#list model.getProperties() as prop>
		private var _${prop.getName()}:${prop.getTypeName()};

		</#list>

		// Getters / Setters

		<#list model.getProperties() as prop>
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
