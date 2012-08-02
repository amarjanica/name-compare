$ ->
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
      url: '/sandbox/name-compare/api'
      data:
        param: JSON.stringify out
      success: (response) ->
        data = response
        console.log 'Length: ', data.length
        console.log 'Type: ', typeof data
        #for row in data
          #console.log row
      error: (response) ->
        responseField.html '<span class="label label-important">Error '+response.status+': '+response.statusText+'</span>'

    false

  return
