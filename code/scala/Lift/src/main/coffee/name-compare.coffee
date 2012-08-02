$ ->
  formRoot = $('#name-compare')

  formRoot.find('button.submit').click (e) ->
    out = []
    formRoot.find('input, textarea').each ->
      fieldName = $(@)[0].name
      fieldValue = if $(@)[0].type is 'checkbox' then $(@).is(':checked') else $(@).val()
      field = {}
      field[fieldName] = fieldValue
      out.push field

    $.ajax
      type: 'post'
      url: 'https://platform.instantor-local.com/sandbox/name-compare/api/send'
      data: JSON.stringify out
      success: (response) ->
        console.info response
      error: (response) ->
        console.warn response

    false

  return