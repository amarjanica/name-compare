$ ->
  formRoot = $('#name-compare')

  formRoot.find('button.submit').click (e) ->
    responseField = $('#response .results')
    responseField.find('.status').html 'Working&hellip;'

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
        responseField.find('.status').text ''

        table = responseField.find 'table tbody'
        list = responseField.find 'textarea'

        i = 1
        for row in response
          perc = row.percentage.toFixed 2
          status = if row.decision is 'Yes' then 'label-success' else if row.decision is 'No' then 'label-important' else 'label-warning'
          decision = "<span class=\"label #{status}\">#{row.decision}</span>"
          table.append "<tr><td class=\"center\">#{i}</td><td>#{row.srcname}</td><td>#{row.dstname}</td><td class=\"center\">#{row.description}</td><td class=\"right\">#{perc}%</td><td class=\"center\">#{decision}</td></tr>"
          list.append "#{row.srcname};#{row.dstname};#{row.description};#{row.percentage.toFixed(2)};#{row.decision}\r\n"
          i++

        responseField.find('table').show()
        list.attr 'rows', response.length+1

        $('#response .js-data-toggler').show()
        return

      error: (response) ->
        responseField.find('.status').removeClass('muted').html '<span class="label label-important">Error '+response.status+': '+response.statusText+'</span>'
        return
    false

  $('#response .js-data-toggler a').click ->
    $(@).parents('ul').children('li').removeClass 'active'
    $(@).parent().addClass 'active'
    target = $(@).attr 'href'
    $('#response .js-resultlist').hide()
    $(target).show()
    false

  return
