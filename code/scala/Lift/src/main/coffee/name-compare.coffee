$ ->
  serverPath = 'https://'+window.location.host
  formRoot = $('#name-compare')

  formRoot.find('button.submit').click (e) ->
    responseField = $('#response .result')
    responseField.html 'Working&hellip;'

    out = []
    formRoot.find('input, textarea').each ->
      fieldName = $(@)[0].name
      fieldValue = if $(@)[0].type is 'checkbox' then $(@).is(':checked') else $(@).val()
      field = {}
      field[fieldName] = fieldValue
      out.push field

    $.ajax
      type: 'post'
      url: serverPath+'/sandbox/name-compare/api/send'
      data: JSON.stringify out
      success: (response) ->
        console.info response
      error: (response) ->
        responseField.html '<span class="label label-important">Error '+response.status+': '+response.statusText+'</span>'

    false

  return