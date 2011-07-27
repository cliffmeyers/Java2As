package
{

<#list transferObjects as dto>
	import ${dto.qualifiedName};
</#list>

	public class TransferObjectManifest
	{
		<#list transferObjects as dto>
		public static const ${dto.simpleName?lower_case}${dto_index}:${dto.simpleName} = null;
		</#list>
	}
}