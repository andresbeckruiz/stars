<#assign content>

<div class="center">


  <h1 id="header1"> Stars </h1>

  <p> Search by Neighbors
  <form method="POST" action="/neighbors">
    <div class="container">
      <textarea name="text" id="text"></textarea><br>
      <input type="checkbox" id="naive" name="method" value="naive">
      <label for="naive">Naive Method</label><br>
    </div>
    <input type="submit">
  </form>
  </p>

  <p> Search by Radius
  <form method="POST" action="/radius">
    <div class="container">
      <textarea name="text" id="text"></textarea><br>
      <input type="checkbox" id="naive" name="method" value="naive">
      <label for="naive">Naive Method</label><br>
    </div>
    <input type="submit">
  </form>
  </p>

  <h2> Stars Found: </h2>

  <div>
  ${results}
  </div>
</div>

</#assign>
<#include "main.ftl">